package wzjtech.document.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import wzjtech.document.CatalogInfo;

@WritingConverter
public class EnablePluginWriteConverter implements Converter<CatalogInfo.EnablePlugin, String> {
  @Override
  public String convert(CatalogInfo.EnablePlugin source) {
    return source.getValue();
  }
}
