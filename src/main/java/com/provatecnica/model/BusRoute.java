package com.provatecnica.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "BUSROUTE")
public class BusRoute extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_BUSROUTE", nullable=false)
    private Long idBusRoute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BUSLINE")
	private BusLine busLine;

    @Column(name="LATITUDE", nullable=false)
    private Double latitude;

    @Column(name="LONGITUDE", nullable=false)
    private Double longitude;

    public BusRoute(BusLine busLine, Double latitude, Double longitude) {
    	this.busLine = busLine;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BusRoute() {}

    public Long getIdBusRoute() {
        return idBusRoute;
    }

    public void setIdBusRoute(Long idBusRoute) {
        this.idBusRoute = idBusRoute;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
