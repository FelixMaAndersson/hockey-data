package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.Calendar;



@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "league_id")
	private League league;

	private String name;

	public Team() {}

	public Team(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
