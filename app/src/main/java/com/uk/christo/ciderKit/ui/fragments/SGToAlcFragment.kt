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
import com.uk.christo.ciderKit.databinding.ActivitySgtoAlcBinding
import com.uk.christo.ciderKit.domain.model.AlcoholCalculationInput
import com.uk.christo.ciderKit.domain.model.BrewingCalculations

class SGToAlcFragment : Fragment() {
    
    private var _binding: ActivitySgtoAlcBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivitySgtoAlcBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupInitialState()
        setupTextWatchers()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun setupInitialState() {
        binding.apply {
            OGValue.hint = getString(R.string.enterValueHint)
            TGValue.hint = getString(R.string.enterValueHint)
            
            // Force numeric input programmatically
            OGValue.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            TGValue.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
    }
    
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateAlcohol()
            }
        }
        
        binding.apply {
            OGValue.addTextChangedListener(textWatcher)
            TGValue.addTextChangedListener(textWatcher)
        }
    }
    
    private fun calculateAlcohol() {
        val ogText = binding.OGValue.text.toString()
        val tgText = binding.TGValue.text.toString()
        
        if (ogText.isNotBlank() && tgText.isNotBlank()) {
            try {
                val originalGravity = ogText.toFloat()
                val targetGravity = tgText.toFloat()
                
                val input = AlcoholCalculationInput(originalGravity, targetGravity)
                val result = BrewingCalculations.calculateAlcoholPercentage(input)
                
                binding.AlcValue.text = String.format("%.2f", result.alcoholPercentage)
            } catch (e: NumberFormatException) {
                binding.AlcValue.text = ""
            }
        } else {
            binding.AlcValue.text = ""
        }
    }
}