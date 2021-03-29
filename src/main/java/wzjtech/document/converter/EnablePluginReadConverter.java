package wzjtech.document.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import wzjtech.document.CatalogInfo;

/**
 * Reading the value from mongodb and convert it to enum
 */
@ReadingConverter
public class EnablePluginReadConverter implements Converter<String, CatalogInfo.EnablePlugin> {
  @Override
  public CatalogInfo.EnablePlugin convert(String source) {
    return CatalogInfo.EnablePlugin.of(source);
  }
}
