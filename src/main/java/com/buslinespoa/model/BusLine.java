package com.buslinespoa.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@Entity
public class BusLine{

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

	public BusLine(Long idBusLine, String code, String name, List<BusRoute> busRouteLis) {
		this.idBusLine = idBusLine;
		this.code = code;
		this.name = name;
		this.busRoutes = busRouteLis;
	}
	public BusLine(){}

}
