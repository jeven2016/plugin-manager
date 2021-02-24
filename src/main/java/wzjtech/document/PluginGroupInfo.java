package wzjtech.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Getter
@Setter
public class PluginGroupInfo {

  //  MongoDB CRUD operations (insert, update, find, remove) all operate on top-level documents exclusively
  //  Embedded documents are always returned within the parent document.
  //  The _id field is a required field of the parent document, and is typically not necessary or present in
  //  embedded documents
  @MongoId(value = FieldType.OBJECT_ID)
  private String id;

  @Indexed(unique = true)
  private String name;

  private String description;

  @Version
  private Long version;

  // @CreatedDate will always be null if you didn't add
  // the @Version to your model class. It works with @Version
  @CreatedDate
  private Instant createdDate;
}
