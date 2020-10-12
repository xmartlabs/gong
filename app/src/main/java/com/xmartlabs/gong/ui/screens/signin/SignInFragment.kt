package com.xmartlabs.gong.ui.screens.signin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.xmartlabs.gong.R
import com.xmartlabs.gong.databinding.FragmentSigninBinding
import com.xmartlabs.gong.device.common.getOrNull
import com.xmartlabs.gong.ui.common.BaseViewBindingFragment
import com.xmartlabs.gong.ui.common.extensions.observeResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

/**
 * Created by mirland on 25/04/20.
 */
class SignInFragment : BaseViewBindingFragment<FragmentSigninBinding>() {
  private val viewModel: SignInFragmentViewModel by viewModel()

  override fun inflateViewBinding(): FragmentSigninBinding =
      FragmentSigninBinding.inflate(layoutInflater)

  @ExperimentalTime
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
      ComposeView(requireContext()).apply {
        setupViewModelCallbacks()
        setContent {
          val user by viewModel.userLiveData.observeAsState(initial = "")
          val password by viewModel.passwordLiveData.observeAsState(initial = "")
          val time by viewModel.viewModelTime.observeAsState()
          SignInContent(
              user,
              password,
              time?.getOrNull() ?: 0L.milliseconds,
              { viewModel.updateUser(it) },
              { viewModel.updatePassword(it) },
              { viewModel.signIn() }
          )
        }
      }

  @SuppressLint("SetTextI18n")
  @OptIn(ExperimentalTime::class)
  private fun setupViewModelCallbacks() = with(viewModel) {
    signIn.observeResult(viewLifecycleOwner,
        onFailure = { throwable ->
          if (throwable is SecurityException) {
            Toast.makeText(
                requireContext(),
                "password or username is wrong, try with userUd = 'xmartlabs', password 'xmartlabs'",
                Toast.LENGTH_SHORT
            ).show()
          }
        },
        onSuccess = {
          router.navigate(
              SignInFragmentDirections.actionSignInFragmentToWelcomeFragment()
          )
        }
    )
  }

  @Suppress("MagicNumber", "LongMethod")
  @ExperimentalTime
  @Preview
  @Composable
  fun SignInContent(
      user: String = "xmartlabs",
      password: String = "xmartlabs",
      duration: Duration = 0L.milliseconds,
      onUserEdited: (String) -> Unit = {},
      onPasswordEdited: (String) -> Unit = {},
      onButtonClick: () -> Unit = {},
  ) {
    Surface(color = Color.White) {
      ConstraintLayout(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        val (hiAllTextView, userIdEditText, passwordEditText, signInButton, viewModelTimeTextView) = createRefs()

        Text(text = "Hi all", fontSize = 20.sp, modifier = Modifier.constrainAs(hiAllTextView) {
          end.linkTo(parent.end)
          start.linkTo(parent.start)
          top.linkTo(parent.top, margin = 150.dp)
        })
        TextField(
            value = user,
            label = { Text("Username") },
            onValueChange = onUserEdited,
            textStyle = TextStyle(fontSize = 20.sp),
            backgroundColor = colorResource(id = android.R.color.transparent),
            modifier = Modifier
                .constrainAs(userIdEditText) {
                  end.linkTo(parent.end)
                  start.linkTo(parent.start)
                  top.linkTo(hiAllTextView.bottom)
                }
                .width(200.dp)
        )
        TextField(
            value = password,
            label = { Text("Password") },
            onValueChange = onPasswordEdited,
            keyboardType = KeyboardType.Password,
            textStyle = TextStyle(fontSize = 20.sp),
            backgroundColor = colorResource(id = android.R.color.transparent),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .constrainAs(passwordEditText) {
                  end.linkTo(parent.end)
                  start.linkTo(parent.start)
                  top.linkTo(userIdEditText.bottom)
                }
                .width(200.dp)
        )
        Text(
            text = "View model time, ${duration.inSeconds} seconds",
            modifier = Modifier.constrainAs(viewModelTimeTextView) {
              end.linkTo(parent.end, margin = 5.dp)
              bottom.linkTo(signInButton.top)
            }
        )
        Button(
            onClick = onButtonClick,
            backgroundColor = colorResource(id = android.R.color.darker_gray),
            modifier = Modifier.fillMaxWidth().constrainAs(signInButton) {
              bottom.linkTo(parent.bottom, margin = 15.dp)
              end.linkTo(parent.end)
              start.linkTo(parent.start)
            }.padding(10.dp)
        ) {
          Text(text = "Sign In".toUpperCase(Locale.ROOT), color = Color.White, textAlign = TextAlign.Center)
        }
      }
    }
  }
}
