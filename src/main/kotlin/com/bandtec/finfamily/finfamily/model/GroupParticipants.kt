package com.bandtec.finfamily.finfamily.model

import javax.persistence.*

@Entity
@Table(name = "group_participants")
class GroupParticipants(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id : Int = 0,
  @Column(name = "user_id")
  val userId : Int = 0,
  @Column(name = "group_id")
  val groupId : Int = 0,
  @Column(name = "is_manager")
  val isManager : Boolean = true

)