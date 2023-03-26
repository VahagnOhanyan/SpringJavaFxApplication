package ohanyan.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "calendar", schema = "public", catalog = "myDb")
public class CalendarEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "calendar_id")
    private int calendarId;
    @Column(name = "year")
    private int year;
    @Column(name = "month")
    private int month;
    @Column(name = "day")
    private int day;
    @Column(name = "type")
    private String type;

}
