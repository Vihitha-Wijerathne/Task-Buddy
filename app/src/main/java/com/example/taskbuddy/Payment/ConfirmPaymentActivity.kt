package com.example.taskbuddy.Payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.example.taskbuddy.R

class ConfirmPaymentActivity : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_payment)

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
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Handle the authentication error
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(
                            this@ConfirmPaymentActivity,
                            "Authentication failed: $errString",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )

        val promptInfo = PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Confirm your identity")
            .setDeviceCredentialAllowed(true) // Allow device PIN/password as a fallback
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
