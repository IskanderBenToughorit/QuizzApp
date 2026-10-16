package com.example.quizzapp

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizUnitTest {

    private val questions: List<Question> = listOf(
        Question(
            questionText = "Quel discipline sportive utilise ce ballon ?",
            imageResId = R.drawable.sport_football,
            options = listOf("Football", "Basketball", "Tennis"),
            correctAnswerIndex = 0,
            contentDescription = "Un ballon de football blanc et noir",
        ),
        Question(
            questionText = "Quel sport se joue avec ce ballon orange ?",
            imageResId = R.drawable.sport_basketball,
            options = listOf("Volleyball", "Basketball", "Handball"),
            correctAnswerIndex = 1,
            contentDescription = "Un ballon de basketball orange avec des lignes noires",
        ),
        Question(
            questionText = "À quel sport appartient cette balle ?",
            imageResId = R.drawable.sport_tennis,
            options = listOf("Badminton", "Ping-pong", "Tennis"),
            correctAnswerIndex = 2,
            contentDescription = "Une balle de tennis jaune fluo",
        ),
        Question(
            questionText = "Quelle discipline sportive utilise cet équipement ?",
            imageResId = R.drawable.sport_cycling,
            options = listOf("Cyclisme", "Athlétisme", "Natation"),
            correctAnswerIndex = 0,
            contentDescription = "Un vélo de course pour le cyclisme",
        ),
        Question(
            questionText = "Que représente cet objet symbole de victoire sportive ?",
            imageResId = R.drawable.sport_trophy,
            options = listOf("Médaille", "Trophée / Coupe", "Bannière"),
            correctAnswerIndex = 1,
            contentDescription = "Un trophée ou une coupe dorée avec une étoile",
        ),
    )

    @Test
    fun quiz_hasExactlyFiveQuestions() {
        assertEquals(5, questions.size)
    }

    @Test
    fun eachQuestion_hasThreeOptionsAndValidAnswerIndex() {
        for (q in questions) {
            assertEquals(3, q.options.size)
            assertTrue(q.correctAnswerIndex in 0..2)
            assertNotNull(q.options[q.correctAnswerIndex])
            assertTrue(q.questionText.isNotEmpty())
            assertTrue(q.contentDescription.isNotEmpty())
        }
    }

    @Test
    fun scoring_perfectScoreCalculation() {
        var score = 0
        val userChoices = listOf(0, 1, 2, 0, 1) // All correct answers

        for (i in questions.indices) {
            if (userChoices[i] == questions[i].correctAnswerIndex) {
                score++
            }
        }

        assertEquals(5, score)
    }

    @Test
    fun scoring_zeroScoreCalculation() {
        var score = 0
        val userChoices = listOf(1, 0, 0, 1, 0) // All wrong answers

        for (i in questions.indices) {
            if (userChoices[i] == questions[i].correctAnswerIndex) {
                score++
            }
        }

        assertEquals(0, score)
    }
}
