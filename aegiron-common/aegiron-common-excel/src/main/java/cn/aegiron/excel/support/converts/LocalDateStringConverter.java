package cn.aegiron.excel.support.converts;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author panda
 * @version 1.0
 * @ClassName LocalDateStringConverter.java
 * @Description 自定义日期(不存储时间或时区)格式转换器
 * @since 2026/2/8 18:00
 */
public enum LocalDateStringConverter implements Converter<LocalDate> {


    INSTANCE;

    @Override
    public Class supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws ParseException {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return LocalDate.parse(cellData.getStringValue());
        } else {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern(contentProperty.getDateTimeFormatProperty().getFormat());
            return LocalDate.parse(cellData.getStringValue(), formatter);
        }
    }

    @Override
    public WriteCellData<String> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        DateTimeFormatter formatter;
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        } else {
            formatter = DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat());
        }
        return new WriteCellData<>(value.format(formatter));
    }
}
