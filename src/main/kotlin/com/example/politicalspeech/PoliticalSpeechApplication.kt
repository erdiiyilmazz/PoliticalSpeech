package com.example.politicalspeech

import DataColumns
import EvaluationResponse
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@SpringBootApplication
class PoliticalSpeechApplication

fun main(args: Array<String>) {
    runApplication<PoliticalSpeechApplication>(*args)
}

@RestController
class PoliticalSpeechContainer {
    @GetMapping("/evaluation")
    fun parseCsv(@RequestParam allRequestParams: Map<String, String>): EvaluationResponse {

        var i = 1
        var url: URL
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val listData: ArrayList<DataColumns> = ArrayList()

        // Loop through as many as csv files
        // Parses each line and put delimiter via ;
        allRequestParams.forEach { requestParam ->
            if (isValidUrl(requestParam.value)) {
                url = URL(requestParam.value)
                if (isRealUrl(url)) {
                    url.openStream().bufferedReader().use { reader ->
                        reader.lineSequence().drop(1).map { line ->
                            line.split(";").let {
                                if (it.size == 4) {
                                    if (isValidData(it[2].trim(), formatter, it[3].trim())) {
                                        listData.add(
                                            DataColumns(
                                                speaker = it[0].trim(),
                                                topic = it[1].trim(),
                                                date = LocalDate.parse(it[2].trim(), formatter),
                                                words = it[3].trim().toInt()
                                            )
                                        )
                                    }
                                }
                            }
                        }.toList()
                    }
                }
            }
            i++
        }
        // Which politician gave the most speeches in 2013?
        val mostSpeechesInYear: String = listData.filter { it.date.year == 2013 }
            .groupBy { it.speaker }
            .mapValues { (_, v) -> v.sumOf { it.words } }
            .maxByOrNull { it.value }
            ?.key.toString()
        // Which politician gave the most speeches on "homeland security"?
        val mostSpeechesSecurity: String = listData.filter { it.topic.contains("homeland security", ignoreCase = true) }
            .distinctBy { it.speaker }
            .singleOrNull()
            ?.speaker.toString()
        // Which politician spoke the fewest words overall?
        val leastWords: String = listData.groupBy { it.speaker }
            .mapValues { (_, v) -> v.sumOf { it.words } }
            .minByOrNull { it.value }
            ?.key.toString()
        // Returning json response here
        return EvaluationResponse(
            mostSpeeches = mostSpeechesInYear,
            mostSecurity = mostSpeechesSecurity,
            leastWordy = leastWords
        )
    }
    // Checks if requested String is a valid URL
    fun isValidUrl(url: String): Boolean {
        try {
            URL(url).toURI()
            return true
        } catch (e: MalformedURLException) {
            return false
        } catch (e: URISyntaxException) {
            return false
        }
    }
    // Checks if requested website has a domain and accessible
    fun isRealUrl(url: URL): Boolean {
        try {
            url.openStream()
            return true
        } catch (e: Exception) {
            return false
        }
    }
    // Checks if requested file is in the correct date and words are integer format
    fun isValidData(date: String, formatter: DateTimeFormatter, number: String): Boolean {
        try {
            LocalDate.parse(date.trim(), formatter)
            number.trim().toInt()
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
