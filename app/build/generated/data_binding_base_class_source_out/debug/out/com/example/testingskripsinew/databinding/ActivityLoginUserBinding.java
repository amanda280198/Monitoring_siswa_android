// Generated by view binder compiler. Do not edit!
package com.example.testingskripsinew.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import com.example.testingskripsinew.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginUserBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton btnLoginUser;

  @NonNull
  public final Guideline guideline;

  @NonNull
  public final Guideline guideline1;

  @NonNull
  public final Guideline guideline2;

  @NonNull
  public final Guideline guideline3;

  @NonNull
  public final Guideline guideline4;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final EditText passUser;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final EditText usernameUser;

  private ActivityLoginUserBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton btnLoginUser, @NonNull Guideline guideline,
      @NonNull Guideline guideline1, @NonNull Guideline guideline2, @NonNull Guideline guideline3,
      @NonNull Guideline guideline4, @NonNull ImageView imageView4, @NonNull EditText passUser,
      @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3,
      @NonNull EditText usernameUser) {
    this.rootView = rootView;
    this.btnLoginUser = btnLoginUser;
    this.guideline = guideline;
    this.guideline1 = guideline1;
    this.guideline2 = guideline2;
    this.guideline3 = guideline3;
    this.guideline4 = guideline4;
    this.imageView4 = imageView4;
    this.passUser = passUser;
    this.textView = textView;
    this.textView2 = textView2;
    this.textView3 = textView3;
    this.usernameUser = usernameUser;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginUserBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginUserBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login_user, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginUserBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_login_user;
      MaterialButton btnLoginUser = rootView.findViewById(id);
      if (btnLoginUser == null) {
        break missingId;
      }

      id = R.id.guideline;
      Guideline guideline = rootView.findViewById(id);
      if (guideline == null) {
        break missingId;
      }

      id = R.id.guideline1;
      Guideline guideline1 = rootView.findViewById(id);
      if (guideline1 == null) {
        break missingId;
      }

      id = R.id.guideline2;
      Guideline guideline2 = rootView.findViewById(id);
      if (guideline2 == null) {
        break missingId;
      }

      id = R.id.guideline3;
      Guideline guideline3 = rootView.findViewById(id);
      if (guideline3 == null) {
        break missingId;
      }

      id = R.id.guideline4;
      Guideline guideline4 = rootView.findViewById(id);
      if (guideline4 == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = rootView.findViewById(id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.pass_user;
      EditText passUser = rootView.findViewById(id);
      if (passUser == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = rootView.findViewById(id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = rootView.findViewById(id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = rootView.findViewById(id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.username_user;
      EditText usernameUser = rootView.findViewById(id);
      if (usernameUser == null) {
        break missingId;
      }

      return new ActivityLoginUserBinding((ConstraintLayout) rootView, btnLoginUser, guideline,
          guideline1, guideline2, guideline3, guideline4, imageView4, passUser, textView, textView2,
          textView3, usernameUser);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
