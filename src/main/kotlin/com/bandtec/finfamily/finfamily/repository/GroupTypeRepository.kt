package com.bandtec.finfamily.finfamily.repository

import com.bandtec.finfamily.finfamily.model.GroupTypes
import org.springframework.data.repository.CrudRepository

interface GroupTypeRepository: CrudRepository<GroupTypes, Int> {


}