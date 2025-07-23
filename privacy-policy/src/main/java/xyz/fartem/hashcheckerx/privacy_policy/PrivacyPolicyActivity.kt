package xyz.fartem.hashcheckerx.privacy_policy

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.stringResource
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXTopBar
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme

class PrivacyPolicyActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HashCheckerXTheme {
                Scaffold(
                    topBar = {
                        HashCheckerXTopBar(
                            title = "Privacy Policy",
                        )
                    }
                ) {
                    WebView(
                        url = stringResource(R.string.privacy_policy_url),
                    )
                }
            }
        }
    }
}
