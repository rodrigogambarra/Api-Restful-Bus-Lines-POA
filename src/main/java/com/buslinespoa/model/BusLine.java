package com.buslinespoa.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BUSLINE")
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

    public Long getIdBusLine() {
        return idBusLine;
    }

    public void setIdBusLine(Long idBusLine) {
        this.idBusLine = idBusLine;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BusRoute> getBusRoutes() {
        return busRoutes;
    }

    public void setBusRoutes(List<BusRoute> busRoutes) {
        this.busRoutes = busRoutes;
    }

}
