package com.bandtec.finfamily.finfamily.controller

import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.RequestPredicates.contentType
import java.net.MalformedURLException
import java.nio.file.Path
import java.nio.file.Paths


@RestController
@RequestMapping("/api/v1/files")
@Api(value = "Arquivos", description = "Operações relacionadas a Download / Upload de arquivos")
class GetFilesController {

    val fileBasePath = "/home/ubuntu/FinFamily-release.apk"

    @GetMapping("")
    fun downloadFileFromLocal(): ResponseEntity<*>? {
        val path: Path = Paths.get(fileBasePath)
        var resource: Resource? = null
        try {
            resource = UrlResource(path.toUri())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.android.package-archive"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource!!.filename + "\"")
                .body(resource)
    }
}