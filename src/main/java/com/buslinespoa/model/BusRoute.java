package com.buslinespoa.model;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

@Data
@Entity
public class BusRoute extends ResourceSupport{

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
}
