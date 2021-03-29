package wzjtech.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Getter
@Setter
public class CatalogInfo {

  //  MongoDB CRUD operations (insert, update, find, remove) all operate on top-level documents exclusively
  //  Embedded documents are always returned within the parent document.
  //  The _id field is a required field of the parent document, and is typically not necessary or present in
  //  embedded documents
  @MongoId(value = FieldType.OBJECT_ID)
  private String id;

  @Indexed(unique = true)
  private String name;

  @TextIndexed
  private String description;

  @Version
  private Long version;

  // @CreatedDate will always be null if you didn't add
  // the @Version to your model class. It works with @Version
  @CreatedDate
  private Instant createdDate = Instant.now();

  @LastModifiedDate
  private Instant lastModifyDate;

  @Indexed
  private EnablePlugin enablePlugin = EnablePlugin.ALL;

  @Getter
  public static enum EnablePlugin {
    ONE("one"),
    ALL("all");

    private final String value;
    private static final EnablePlugin[] VALUES = EnablePlugin.values();

    EnablePlugin(String value) {
      this.value = value;
    }

    @JsonValue
    public String value() {
      return value;
    }

    @JsonCreator
    public static EnablePlugin of(String val) {
      if (!StringUtils.hasText(val)) {
        return null;
      }
      for (var existingValue : VALUES) {
        if (val.equals(existingValue.getValue())) {
          return existingValue;
        }
      }
      return null;
    }

//    @Override
    public String toString() {
      return value;
    }
  }
}
