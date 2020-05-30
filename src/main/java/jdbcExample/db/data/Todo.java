package jdbcExample.db.data;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class Todo {

    private int id;
    private String name;
    private Timestamp createdDate;
    private Date deadline;
    private String status;
    private int userId;
}
