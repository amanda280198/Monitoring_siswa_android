// Generated by view binder compiler. Do not edit!
package com.example.testingskripsinew.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.example.testingskripsinew.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMonitoringKelasBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final MaterialButton btnKelasSelesai;

  @NonNull
  public final Guideline guideline9;

  @NonNull
  public final RecyclerView rvListMasuk;

  @NonNull
  public final TextView textJudul;

  @NonNull
  public final TextView textTotal;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final MaterialToolbar toolbar;

  private ActivityMonitoringKelasBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppBarLayout appBarLayout, @NonNull MaterialButton btnKelasSelesai,
      @NonNull Guideline guideline9, @NonNull RecyclerView rvListMasuk, @NonNull TextView textJudul,
      @NonNull TextView textTotal, @NonNull TextView textView7, @NonNull MaterialToolbar toolbar) {
    this.rootView = rootView;
    this.appBarLayout = appBarLayout;
    this.btnKelasSelesai = btnKelasSelesai;
    this.guideline9 = guideline9;
    this.rvListMasuk = rvListMasuk;
    this.textJudul = textJudul;
    this.textTotal = textTotal;
    this.textView7 = textView7;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMonitoringKelasBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMonitoringKelasBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_monitoring_kelas, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMonitoringKelasBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appBarLayout;
      AppBarLayout appBarLayout = rootView.findViewById(id);
      if (appBarLayout == null) {
        break missingId;
      }

      id = R.id.btn_kelas_selesai;
      MaterialButton btnKelasSelesai = rootView.findViewById(id);
      if (btnKelasSelesai == null) {
        break missingId;
      }

      id = R.id.guideline9;
      Guideline guideline9 = rootView.findViewById(id);
      if (guideline9 == null) {
        break missingId;
      }

      id = R.id.rvListMasuk;
      RecyclerView rvListMasuk = rootView.findViewById(id);
      if (rvListMasuk == null) {
        break missingId;
      }

      id = R.id.textJudul;
      TextView textJudul = rootView.findViewById(id);
      if (textJudul == null) {
        break missingId;
      }

      id = R.id.text_total;
      TextView textTotal = rootView.findViewById(id);
      if (textTotal == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = rootView.findViewById(id);
      if (textView7 == null) {
        break missingId;
      }

      id = R.id.toolbar;
      MaterialToolbar toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }

      return new ActivityMonitoringKelasBinding((ConstraintLayout) rootView, appBarLayout,
          btnKelasSelesai, guideline9, rvListMasuk, textJudul, textTotal, textView7, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
