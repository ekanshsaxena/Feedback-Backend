package com.example.feedbackbackend.service

import com.example.feedbackbackend.model.Feedback
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service

@Service
class FeedbackService(
    val db: JdbcTemplate
) {
    private val logger: Logger = LoggerFactory.getLogger(FeedbackService::class.java)

    fun fetchFeedback(): List<Feedback> {
        logger.info("Fetching feedback")
        val feedbackList = mutableListOf<Feedback>()
        try {
            db.query("select * from Feedback") { rs, _ ->
                feedbackList.add(Feedback(
                    rs.getInt("id"),
                    rs.getString("text"),
                    rs.getInt("rating")
                ))
            }
        }catch (ex: Exception){
            logger.error("Error Occurred while fetching feedback: $ex")
            throw ex
        }

        logger.info("Fetched feedback: $feedbackList")
        return feedbackList
    }

    fun addFeedback(feedback: Feedback): Feedback {
        logger.info("Feedback received: $feedback")
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val updatedFeedback: Feedback
        try {
            db.update({ connection ->
                val ps = connection.prepareStatement(
                    "insert into Feedback (text, rating) values (?, ?)",
                    arrayOf("id")
                )
                ps.setString(1, feedback.text)
                ps.setInt(2, feedback.rating)
                ps
            }, keyHolder)
            updatedFeedback = feedback.copy(id = keyHolder.key?.toInt() ?: 0)
        } catch (ex: DataIntegrityViolationException) {
            logger.error("Error Occurred while adding feedback: $ex")
            throw ex
        }
        logger.info("Feedback added: $updatedFeedback")
        return updatedFeedback
    }

    fun deleteFeedback(id: Int) {
        logger.info("Deleting feedback with id: $id")
        try {
            db.update("delete from Feedback where id = ?", id)
        }catch(ex: Exception){
            logger.error("Error Occurred while deleting feedback: $ex")
            throw ex
        }
        logger.info("Feedback deleted with id: $id")
    }

    fun updateFeedback(id: Int, feedback: Feedback): Feedback {
        logger.info("Updating feedback with id: $id")
        try {
            db.update("update Feedback set text = ?, rating = ? where id = ?",
                feedback.text,
                feedback.rating,
                id
            )
        }catch(ex: Exception){
            logger.error("Error Occurred while updating feedback: $ex")
            throw ex
        }
        logger.info("Feedback updated with id: $id")
        return feedback
    }

}