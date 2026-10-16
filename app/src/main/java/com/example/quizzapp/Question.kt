package com.example.quizzapp

import androidx.annotation.DrawableRes

/**
 * Structure de données représentant une question du quiz.
 *
 * @property questionText Texte de la question affiché à l'utilisateur.
 * @property imageResId Identifiant de la ressource image associée (R.drawable.*).
 * @property options Liste de trois choix de réponses possibles.
 * @property correctAnswerIndex Indice (0, 1 ou 2) de la bonne réponse dans la liste des options.
 * @property contentDescription Description textuelle de l'image pour l'accessibilité.
 */
data class Question(
    val questionText: String,
    @param:DrawableRes val imageResId: Int,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val contentDescription: String,
)
