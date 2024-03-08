package com.example.feedbackbackend.controller

import com.example.feedbackbackend.model.Feedback
import com.example.feedbackbackend.service.FeedbackService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FeedbackController(
    val feedbackService: FeedbackService
) {
    private val logger: Logger = LoggerFactory.getLogger(FeedbackController::class.java)
    @GetMapping("/")
    fun getFeedback(): List<Feedback> {
        logger.info("Inside getFeedback method")
        return feedbackService.fetchFeedback()
    }

    @PostMapping("/addFeedback")
    fun addFeedback(@RequestBody feedback: Feedback): Feedback {
        logger.info("Inside addFeedback method")
        return feedbackService.addFeedback(feedback)
    }

    @DeleteMapping("/deleteFeedback/{id}")
    fun deleteFeedback(@PathVariable id: Int) {
        logger.info("Inside deleteFeedback method")
        feedbackService.deleteFeedback(id)
    }

    @PutMapping("/updateFeedback/{id}")
    fun updateFeedback(@PathVariable id: Int, @RequestBody feedback: Feedback): Feedback {
        logger.info("Inside updateFeedback method")
        return feedbackService.updateFeedback(id, feedback)
    }

}