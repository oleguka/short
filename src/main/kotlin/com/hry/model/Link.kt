package com.hry.model

import javax.persistence.*


@Entity
@Table(name = "links")
data class Link(
    var text: String = "",
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "links_sequence")
//    @SequenceGenerator(name = "links_sequence", sequenceName = "links_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
)