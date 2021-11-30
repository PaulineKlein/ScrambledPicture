package com.pklein.scrambledpicture.presentation.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pklein.scrambledpicture.R
import com.pklein.scrambledpicture.data.model.GameData
import com.pklein.scrambledpicture.databinding.ActivityMainBinding
import com.pklein.scrambledpicture.utils.bind
import com.pklein.scrambledpicture.utils.distinctUntilChanged
import com.pklein.scrambledpicture.utils.map


class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleViewModelChanges()
        instantiateView()
    }

    private fun instantiateView() {
        showLoader()
        homeViewModel.handle(HomeAction.GetAllGame)

        binding.buttonValidate.setOnClickListener {
            homeViewModel.handle(HomeAction.CheckAnswer(binding.editTextAnswer.text.toString()))
        }
        binding.buttonContinue.setOnClickListener {
            homeViewModel.handle(HomeAction.GetNextGame)
            binding.editTextAnswer.setText("")
        }
    }

    private fun handleViewModelChanges() {
        homeViewModel.state.map { it.dataToShow }
            .distinctUntilChanged()
            .bind(this, this::showGame)
        homeViewModel.state.map { it.isCorrect }
            .bind(this, this::showAnswer)
        homeViewModel.state.map { it.isFinish }
            .distinctUntilChanged()
            .bind(this, this::showEndOfGame)
    }

    private fun showGame(gameData: GameData?) {
        gameData?.let {
            hideLoader()
            binding.buttonValidate.isEnabled = true
            binding.buttonValidate.isClickable = true
            binding.buttonContinue.isEnabled = false
            binding.buttonContinue.isClickable = false
            val step = gameData.id
            binding.textViewTitle.text = getString(R.string.game_message_title, step.toString())
            binding.textViewDesc.text = getString(R.string.game_message_intro)
            val resource = when (gameData.imageName) {
                "cat" -> R.drawable.cat
                "coffee" -> R.drawable.coffee
                "droid" -> R.drawable.droid
                else -> R.drawable.puzzle
            }
            binding.gridLayout.scrambledImage(
                binding.gridLayout.splitBitmap(
                    BitmapFactory.decodeResource(
                        this.resources,
                        resource
                    )
                )
            )
        }
    }

    private fun showAnswer(isCorrect: Boolean?) {
        isCorrect?.let {
            if (it) {
                binding.gridLayout.unScrambledImage(this)
                binding.buttonValidate.isEnabled = false
                binding.buttonValidate.isClickable = false
                binding.buttonContinue.isEnabled = true
                binding.buttonContinue.isClickable = true
                binding.textViewDesc.text = getString(R.string.game_message_success)
            } else {
                binding.textViewDesc.text = getString(R.string.game_message_error)
            }
        }
    }

    private fun showEndOfGame(isFinish: Boolean) {
        if (isFinish) {
            binding.buttonValidate.isEnabled = false
            binding.buttonValidate.isClickable = false
            binding.buttonContinue.isEnabled = false
            binding.buttonContinue.isClickable = false
            binding.textViewDesc.text = getString(R.string.game_message_finish)
        }
    }

    private fun showLoader() {
        binding.loader.visibility = View.VISIBLE
        binding.contentView.visibility = View.GONE
        binding.loader.show()
    }

    private fun hideLoader() {
        binding.loader.hide()
        binding.loader.visibility = View.GONE
        binding.contentView.visibility = View.VISIBLE
    }
}