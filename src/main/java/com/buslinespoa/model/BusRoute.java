package com.buslinespoa.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.Objects;

@Data
@Builder
@Entity
public class BusRoute{

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

	public BusRoute(Long id, BusLine busLine, Double latitude, Double longitude) {
		this.idBusRoute = id;
		this.busLine = busLine;
		this.latitude = latitude;
		this.longitude = longitude;
	}

    public BusRoute() {}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		BusRoute busRoute = (BusRoute) o;
		return Objects.equals(idBusRoute, busRoute.idBusRoute) && Objects.equals(busLine, busRoute.busLine) && Objects.equals(latitude, busRoute.latitude) && Objects.equals(longitude, busRoute.longitude);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), idBusRoute, busLine, latitude, longitude);
	}
}
