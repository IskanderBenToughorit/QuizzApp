package com.example.quizzapp

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizzapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // 1. Liste de 5 questions statiques sur le thème du Sport
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

    // Variables d'état du quiz
    private var currentQuestionIndex: Int = 0
    private var score: Int = 0
    private var isAnswered: Boolean = false
    private var selectedOptionIndex: Int = -1
    private var isQuizFinished: Boolean = false

    companion object {
        private const val KEY_QUESTION_INDEX = "key_question_index"
        private const val KEY_SCORE = "key_score"
        private const val KEY_IS_ANSWERED = "key_is_answered"
        private const val KEY_SELECTED_OPTION_INDEX = "key_selected_option_index"
        private const val KEY_IS_QUIZ_FINISHED = "key_is_quiz_finished"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Restauration de l'état lors d'un changement de configuration (rotation)
        savedInstanceState?.let { bundle ->
            currentQuestionIndex = bundle.getInt(KEY_QUESTION_INDEX, 0)
            score = bundle.getInt(KEY_SCORE, 0)
            isAnswered = bundle.getBoolean(KEY_IS_ANSWERED, false)
            selectedOptionIndex = bundle.getInt(KEY_SELECTED_OPTION_INDEX, -1)
            isQuizFinished = bundle.getBoolean(KEY_IS_QUIZ_FINISHED, false)
        }

        setupListeners()

        if (isQuizFinished) {
            displayFinalResult()
        } else {
            displayCurrentQuestion()
            if (isAnswered && selectedOptionIndex != -1) {
                // Si la question avait déjà été répondue avant rotation, restaurer le résultat visuel
                restoreAnswerState(selectedOptionIndex)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_QUESTION_INDEX, currentQuestionIndex)
        outState.putInt(KEY_SCORE, score)
        outState.putBoolean(KEY_IS_ANSWERED, isAnswered)
        outState.putInt(KEY_SELECTED_OPTION_INDEX, selectedOptionIndex)
        outState.putBoolean(KEY_IS_QUIZ_FINISHED, isQuizFinished)
    }

    private fun setupListeners() {
        binding.btnChoix1.setOnClickListener { checkAnswer(0) }
        binding.btnChoix2.setOnClickListener { checkAnswer(1) }
        binding.btnChoix3.setOnClickListener { checkAnswer(2) }

        binding.btnNext.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                isAnswered = false
                selectedOptionIndex = -1
                displayCurrentQuestion()
            } else {
                isQuizFinished = true
                displayFinalResult()
            }
        }

        binding.btnRestart.setOnClickListener {
            restartQuiz()
        }
    }

    private fun displayCurrentQuestion() {
        val currentQuestion = questions[currentQuestionIndex]

        binding.layoutQuestionCard.visibility = View.VISIBLE
        binding.layoutResult.visibility = View.GONE

        // En-tête et barre de progression
        binding.txtScore.text = getString(R.string.score_format, score, questions.size)
        binding.progressQuiz.progress = currentQuestionIndex
        binding.txtQuestionNumber.text = getString(R.string.question_number_format, currentQuestionIndex + 1, questions.size)

        // Image et question
        binding.imgQuestion.setImageResource(currentQuestion.imageResId)
        binding.imgQuestion.contentDescription = currentQuestion.contentDescription
        binding.txtQuestion.text = currentQuestion.questionText

        // Choix de réponses
        binding.btnChoix1.text = currentQuestion.options[0]
        binding.btnChoix2.text = currentQuestion.options[1]
        binding.btnChoix3.text = currentQuestion.options[2]

        // Réinitialiser le style et l'état des boutons
        resetChoiceButtons()

        // Zone de retour d'information et bouton suivant
        binding.txtFeedback.visibility = View.GONE
        binding.btnNext.visibility = View.GONE
    }

    private fun checkAnswer(selectedIndex: Int) {
        if (isAnswered) return // Empêche la sélection multiple

        isAnswered = true
        selectedOptionIndex = selectedIndex

        val currentQuestion = questions[currentQuestionIndex]
        val isCorrect = (selectedIndex == currentQuestion.correctAnswerIndex)

        if (isCorrect) {
            score++
        }

        // Mettre à jour la barre de progression (nombre de questions répondues)
        binding.progressQuiz.progress = currentQuestionIndex + 1
        binding.txtScore.text = getString(R.string.score_format, score, questions.size)

        applyAnswerStyles(selectedIndex, currentQuestion.correctAnswerIndex, isCorrect)
    }

    private fun restoreAnswerState(selectedIndex: Int) {
        val currentQuestion = questions[currentQuestionIndex]
        val isCorrect = (selectedIndex == currentQuestion.correctAnswerIndex)

        binding.progressQuiz.progress = currentQuestionIndex + 1
        binding.txtScore.text = getString(R.string.score_format, score, questions.size)

        applyAnswerStyles(selectedIndex, currentQuestion.correctAnswerIndex, isCorrect)
    }

    private fun applyAnswerStyles(selectedIndex: Int, correctIndex: Int, isCorrect: Boolean) {
        val currentQuestion = questions[currentQuestionIndex]
        val choiceButtons = listOf(binding.btnChoix1, binding.btnChoix2, binding.btnChoix3)

        // Désactiver tous les boutons pour empêcher de rééditer la réponse
        choiceButtons.forEach { button ->
            button.isEnabled = false
        }

        val colorCorrect = ContextCompat.getColor(this, R.color.color_correct)
        val colorIncorrect = ContextCompat.getColor(this, R.color.color_incorrect)

        if (isCorrect) {
            // Bouton sélectionné en vert
            choiceButtons[selectedIndex].backgroundTintList = ColorStateList.valueOf(colorCorrect)
            choiceButtons[selectedIndex].setTextColor(ContextCompat.getColor(this, R.color.white))

            binding.txtFeedback.text = getString(R.string.feedback_correct)
            binding.txtFeedback.setTextColor(colorCorrect)
            binding.txtFeedback.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_correct))
        } else {
            // Bouton sélectionné en rouge, bonne réponse en vert
            choiceButtons[selectedIndex].backgroundTintList = ColorStateList.valueOf(colorIncorrect)
            choiceButtons[selectedIndex].setTextColor(ContextCompat.getColor(this, R.color.white))

            choiceButtons[correctIndex].backgroundTintList = ColorStateList.valueOf(colorCorrect)
            choiceButtons[correctIndex].setTextColor(ContextCompat.getColor(this, R.color.white))

            val correctAnswerText = currentQuestion.options[correctIndex]
            binding.txtFeedback.text = getString(R.string.feedback_incorrect, correctAnswerText)
            binding.txtFeedback.setTextColor(colorIncorrect)
            binding.txtFeedback.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_incorrect))
        }

        binding.txtFeedback.visibility = View.VISIBLE

        // Configurer le bouton de navigation
        if (currentQuestionIndex == questions.size - 1) {
            binding.btnNext.text = getString(R.string.btn_view_result)
        } else {
            binding.btnNext.text = getString(R.string.btn_next)
        }
        binding.btnNext.visibility = View.VISIBLE
    }

    private fun resetChoiceButtons() {
        val choiceButtons = listOf(binding.btnChoix1, binding.btnChoix2, binding.btnChoix3)
        choiceButtons.forEach { button ->
            button.isEnabled = true
            button.backgroundTintList = null
            button.setTextColor(ContextCompat.getColor(this, R.color.primary))
        }
    }

    private fun displayFinalResult() {
        binding.layoutQuestionCard.visibility = View.GONE
        binding.layoutResult.visibility = View.VISIBLE

        binding.txtScore.text = getString(R.string.score_format, score, questions.size)
        binding.progressQuiz.progress = questions.size

        val percentage = (score * 100) / questions.size
        binding.txtFinalScore.text = getString(R.string.final_score_format, score, questions.size, percentage)

        binding.txtFinalMessage.text = when (score) {
            5 -> "Félicitations ! Un sans-faute parfait, un vrai champion !"
            4 -> "Très bien ! Belle performance sportive !"
            3 -> "Bon travail ! Vous avez réussi le quiz."
            1, 2 -> "Score moyen. N'hésitez pas à rejouer !"
            else -> "Dommage ! Entraînez-vous et réessayez."
        }
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        isAnswered = false
        selectedOptionIndex = -1
        isQuizFinished = false
        displayCurrentQuestion()
    }
}
