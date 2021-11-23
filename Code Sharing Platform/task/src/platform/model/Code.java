package platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "code")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "uuid")
    @JsonIgnore
    private String uuid;

    @NotBlank
    @Column(name = "code", columnDefinition = "CLOB")
    private String code;

    @Column(name = "date")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String date;

    @Column(name = "deadline")
    @JsonIgnore
    private String deadline;

    @Column(name = "time")
    private Long time;

    @Column(name = "views")
    private Long views;
}
