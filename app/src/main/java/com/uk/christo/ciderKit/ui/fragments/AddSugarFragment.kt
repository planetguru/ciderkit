package com.uk.christo.ciderKit.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uk.christo.ciderKit.R
import com.uk.christo.ciderKit.databinding.FragmentAddsugarBinding
import com.uk.christo.ciderKit.domain.model.BrewingCalculations
import com.uk.christo.ciderKit.domain.model.SugarCalculationInput
import com.uk.christo.ciderKit.data.PreferencesManager

class AddSugarFragment : Fragment() {
    
    private var _binding: FragmentAddsugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesManager: PreferencesManager
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddsugarBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        preferencesManager = PreferencesManager(requireContext())
        
        setupInitialState()
        setupTextWatchers()
        setupRadioGroup()
        loadSavedValues()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun setupInitialState() {
        binding.apply {
            radioMetric.isChecked = true
            radioImperial.isChecked = false
            
            startingSGValue.hint = getString(R.string.enterValueHint)
            targetSGValue.hint = getString(R.string.enterValueHint)
            volumeValue.hint = getString(R.string.enterValueHint)
            
            // Force numeric input programmatically
            startingSGValue.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            targetSGValue.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            volumeValue.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            
            updateLabels()
        }
    }
    
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateSugar()
            }
        }
        
        binding.apply {
            startingSGValue.addTextChangedListener(textWatcher)
            targetSGValue.addTextChangedListener(textWatcher)
            volumeValue.addTextChangedListener(textWatcher)
        }
    }
    
    private fun setupRadioGroup() {
        binding.radioGroup1.setOnCheckedChangeListener { _, checkedId ->
            updateLabels()
            convertVolumeUnit(checkedId)
            calculateSugar()
        }
    }
    
    private fun updateLabels() {
        binding.apply {
            if (radioImperial.isChecked) {
                addSugarLabel.setText(R.string.addSugarLabelImperial)
            } else {
                addSugarLabel.setText(R.string.addSugarLabelMetric)
            }
        }
    }
    
    private fun convertVolumeUnit(checkedId: Int) {
        val volumeText = binding.volumeValue.text.toString()
        if (volumeText.isNotBlank()) {
            val volume = volumeText.toFloatOrNull() ?: return
            val convertedVolume = when (checkedId) {
                R.id.radioMetric -> volume / 1.7598f // Convert from pints to liters
                R.id.radioImperial -> volume * 1.7598f // Convert from liters to pints
                else -> return
            }
            binding.volumeValue.setText(String.format("%.2f", convertedVolume))
        }
    }
    
    private fun calculateSugar() {
        val startingSGText = binding.startingSGValue.text.toString()
        val targetSGText = binding.targetSGValue.text.toString()
        val volumeText = binding.volumeValue.text.toString()
        
        saveCurrentValues()
        
        if (startingSGText.isNotBlank() && targetSGText.isNotBlank() && volumeText.isNotBlank()) {
            try {
                val startingSG = startingSGText.toFloat()
                val targetSG = targetSGText.toFloat()
                val volume = volumeText.toFloat()
                val isImperial = binding.radioImperial.isChecked
                
                val input = SugarCalculationInput(startingSG, targetSG, volume, isImperial)
                val result = BrewingCalculations.calculateSugarToAdd(input)
                
                binding.addSugarValue.setText(String.format("%.2f", result.sugarToAdd))
            } catch (e: NumberFormatException) {
                binding.addSugarValue.setText("")
            }
        } else {
            binding.addSugarValue.setText("")
        }
    }
    
    private fun saveCurrentValues() {
        val startingSG = binding.startingSGValue.text.toString()
        val targetSG = binding.targetSGValue.text.toString()
        val volume = binding.volumeValue.text.toString()
        val isImperial = binding.radioImperial.isChecked
        
        preferencesManager.saveAddSugarData(startingSG, targetSG, volume, isImperial)
    }
    
    private fun loadSavedValues() {
        binding.apply {
            startingSGValue.setText(preferencesManager.getStartingSG())
            targetSGValue.setText(preferencesManager.getTargetSG())
            volumeValue.setText(preferencesManager.getVolume())
            
            val isImperial = preferencesManager.isImperialSelected()
            radioImperial.isChecked = isImperial
            radioMetric.isChecked = !isImperial
            
            updateLabels()
            calculateSugar()
        }
    }
}