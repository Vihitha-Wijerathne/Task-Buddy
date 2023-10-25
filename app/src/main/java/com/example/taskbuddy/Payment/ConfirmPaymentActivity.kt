package com.example.taskbuddy.Payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import android.widget.Toast

class ConfirmPaymentActivity : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // Authentication was successful; you can proceed with your payment logic
                    // The user has successfully authenticated with their biometric data
                    // You can now proceed with your payment logic
                    // For example, call a function to perform the payment.
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Handle the authentication error
                    Toast.makeText(
                        this@ConfirmPaymentActivity,
                        "Authentication failed: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        val promptInfo = PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Confirm your identity")
            .setDeviceCredentialAllowed(true) // Allow device PIN/password as a fallback
            .build()

        // Trigger biometric authentication directly on the current page (e.g., when a button is clicked).
        showBiometricPrompt(promptInfo)
    }

    private fun showBiometricPrompt(promptInfo: BiometricPrompt.PromptInfo) {
        biometricPrompt.authenticate(promptInfo)
    }

}
