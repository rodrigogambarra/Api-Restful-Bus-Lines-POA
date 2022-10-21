package com.buslinespoa.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class BusLine extends ResourceSupport {

    @Id
    @Column(name="ID_BUSLINE", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBusLine;

    @NotEmpty
    @Column(name="CODE", nullable=false)
    private String code;

    @NotEmpty
    @Column(name="NAME", nullable=false)
    private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "busLine", cascade = {CascadeType.REMOVE})
    private List<BusRoute> busRoutes;

    public BusLine(Long idBusLine, String code, String name) {
        this.idBusLine = idBusLine;
        this.code = code;
        this.name = name;
    }

	public BusLine(){}
}
