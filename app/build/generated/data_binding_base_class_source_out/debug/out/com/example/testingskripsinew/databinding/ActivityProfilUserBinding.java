// Generated by view binder compiler. Do not edit!
package com.example.testingskripsinew.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import com.example.testingskripsinew.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfilUserBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnLogout;

  @NonNull
  public final ImageButton buttonBack;

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
  public final ImageView imageView7;

  @NonNull
  public final TextView namaUser;

  @NonNull
  public final TextView npm;

  @NonNull
  public final TextView txtProfilPribadi;

  @NonNull
  public final View view1;

  private ActivityProfilUserBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnLogout,
      @NonNull ImageButton buttonBack, @NonNull Guideline guideline, @NonNull Guideline guideline1,
      @NonNull Guideline guideline2, @NonNull Guideline guideline3, @NonNull Guideline guideline4,
      @NonNull ImageView imageView7, @NonNull TextView namaUser, @NonNull TextView npm,
      @NonNull TextView txtProfilPribadi, @NonNull View view1) {
    this.rootView = rootView;
    this.btnLogout = btnLogout;
    this.buttonBack = buttonBack;
    this.guideline = guideline;
    this.guideline1 = guideline1;
    this.guideline2 = guideline2;
    this.guideline3 = guideline3;
    this.guideline4 = guideline4;
    this.imageView7 = imageView7;
    this.namaUser = namaUser;
    this.npm = npm;
    this.txtProfilPribadi = txtProfilPribadi;
    this.view1 = view1;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfilUserBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfilUserBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profil_user, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfilUserBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_logout;
      Button btnLogout = rootView.findViewById(id);
      if (btnLogout == null) {
        break missingId;
      }

      id = R.id.button_back;
      ImageButton buttonBack = rootView.findViewById(id);
      if (buttonBack == null) {
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

      id = R.id.imageView7;
      ImageView imageView7 = rootView.findViewById(id);
      if (imageView7 == null) {
        break missingId;
      }

      id = R.id.nama_user;
      TextView namaUser = rootView.findViewById(id);
      if (namaUser == null) {
        break missingId;
      }

      id = R.id.npm;
      TextView npm = rootView.findViewById(id);
      if (npm == null) {
        break missingId;
      }

      id = R.id.txtProfilPribadi;
      TextView txtProfilPribadi = rootView.findViewById(id);
      if (txtProfilPribadi == null) {
        break missingId;
      }

      id = R.id.view1;
      View view1 = rootView.findViewById(id);
      if (view1 == null) {
        break missingId;
      }

      return new ActivityProfilUserBinding((ConstraintLayout) rootView, btnLogout, buttonBack,
          guideline, guideline1, guideline2, guideline3, guideline4, imageView7, namaUser, npm,
          txtProfilPribadi, view1);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}