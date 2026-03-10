package cn.aegiron.util;

import cn.aegiron.api.CommonResult;
import cn.aegiron.constant.CommonConstants;
import cn.hutool.core.util.ObjectUtil;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 简化 CommonResult 的链式操作
 * 参考 jlyx-common RetOps 设计
 *
 * @author panda
 * @since 2026/2/26
 */
public class RetOps<T> {

    /**
     * 状态码为成功
     */
    public static final Predicate<CommonResult<?>> CODE_SUCCESS =
            response -> CommonConstants.SUCCESS.equals(response.getCode());

    /**
     * 数据有值
     */
    public static final Predicate<CommonResult<?>> HAS_DATA =
            response -> ObjectUtil.isNotEmpty(response.getData());

    /**
     * 状态码为成功并且有值
     */
    public static final Predicate<CommonResult<?>> DATA_AVAILABLE = CODE_SUCCESS.and(HAS_DATA);

    private final CommonResult<T> original;

    RetOps(CommonResult<T> original) {
        this.original = original;
    }

    public static <T> RetOps<T> of(CommonResult<T> original) {
        return new RetOps<>(Objects.requireNonNull(original));
    }

    /**
     * 查看原始响应
     */
    public CommonResult<T> peek() {
        return original;
    }

    /**
     * 获取响应的 code
     */
    public String getCode() {
        return original.getCode();
    }

    /**
     * 获取响应的 data
     */
    public Optional<T> getData() {
        return Optional.ofNullable(original.getData());
    }

    /**
     * 有条件地读取 data 的值
     *
     * @param predicate 断言函数
     * @return 返回 Optional 包装的 data，如果断言失败返回 empty
     */
    public Optional<T> getDataIf(Predicate<? super CommonResult<?>> predicate) {
        return predicate.test(original) ? getData() : Optional.empty();
    }

    /**
     * 读取 message 的值
     */
    public Optional<String> getMsg() {
        return Optional.ofNullable(original.getMessage());
    }

    /**
     * 对 code 的值进行相等性测试
     */
    public boolean codeEquals(String value) {
        return Objects.equals(original.getCode(), value);
    }

    /**
     * 对 code 的值进行相等性测试
     */
    public boolean codeNotEquals(String value) {
        return !codeEquals(value);
    }

    /**
     * 是否成功
     */
    public boolean isSuccess() {
        return codeEquals(CommonConstants.SUCCESS);
    }

    /**
     * 是否失败
     */
    public boolean notSuccess() {
        return !isSuccess();
    }

    /**
     * 断言 code 的值
     *
     * @param expect 预期的值
     * @param func   用户函数，负责创建异常对象
     * @throws Ex 断言失败时抛出
     */
    public <Ex extends Exception> RetOps<T> assertCode(String expect, Function<? super CommonResult<T>, ? extends Ex> func)
            throws Ex {
        if (codeNotEquals(expect)) {
            throw func.apply(original);
        }
        return this;
    }

    /**
     * 断言成功
     *
     * @param func 用户函数，负责创建异常对象
     * @throws Ex 断言失败时抛出
     */
    public <Ex extends Exception> RetOps<T> assertSuccess(Function<? super CommonResult<T>, ? extends Ex> func)
            throws Ex {
        return assertCode(CommonConstants.SUCCESS, func);
    }

    /**
     * 断言业务数据有值
     *
     * @param func 用户函数，负责创建异常对象
     * @throws Ex 断言失败时抛出
     */
    public <Ex extends Exception> RetOps<T> assertDataNotNull(Function<? super CommonResult<T>, ? extends Ex> func)
            throws Ex {
        if (Objects.isNull(original.getData())) {
            throw func.apply(original);
        }
        return this;
    }

    /**
     * 断言业务数据有值且非空
     *
     * @param func 用户函数，负责创建异常对象
     * @throws Ex 断言失败时抛出
     */
    public <Ex extends Exception> RetOps<T> assertDataNotEmpty(Function<? super CommonResult<T>, ? extends Ex> func)
            throws Ex {
        if (ObjectUtil.isEmpty(original.getData())) {
            throw func.apply(original);
        }
        return this;
    }

    /**
     * 对业务数据(data)转换
     *
     * @param mapper 业务数据转换函数
     * @param <U>    数据类型
     */
    public <U> RetOps<U> map(Function<? super T, ? extends U> mapper) {
        CommonResult<U> result = CommonResult.of(
                original.getCode(),
                original.getMessage(),
                mapper.apply(original.getData()));
        return of(result);
    }

    /**
     * 条件消费(错误代码匹配某个值)
     *
     * @param consumer 消费函数
     * @param codes    错误代码集合，匹配任意一个则调用消费函数
     */
    public void useDataOnCode(Consumer<? super T> consumer, String... codes) {
        useDataIf(o -> Arrays.asList(codes).contains(original.getCode()), consumer);
    }

    /**
     * 条件消费(错误代码表示成功)
     *
     * @param consumer 消费函数
     */
    public void useDataIfSuccess(Consumer<? super T> consumer) {
        useDataIf(CODE_SUCCESS, consumer);
    }

    /**
     * 条件消费
     *
     * @param predicate 断言函数
     * @param consumer  消费函数，断言函数返回 true 时被调用
     */
    public void useDataIf(Predicate<? super CommonResult<T>> predicate, Consumer<? super T> consumer) {
        if (predicate.test(original)) {
            consumer.accept(original.getData());
        }
    }
}
