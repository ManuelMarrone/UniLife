// Generated by view binder compiler. Do not edit!
package com.example.unilife.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.unilife.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityArchivioBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonIndietro;

  @NonNull
  public final ImageButton downloadButton;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView textUnilife;

  @NonNull
  public final TextView textView17;

  @NonNull
  public final ImageButton uploadButton;

  private ActivityArchivioBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button buttonIndietro, @NonNull ImageButton downloadButton,
      @NonNull ConstraintLayout main, @NonNull TextView textUnilife, @NonNull TextView textView17,
      @NonNull ImageButton uploadButton) {
    this.rootView = rootView;
    this.buttonIndietro = buttonIndietro;
    this.downloadButton = downloadButton;
    this.main = main;
    this.textUnilife = textUnilife;
    this.textView17 = textView17;
    this.uploadButton = uploadButton;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityArchivioBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityArchivioBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_archivio, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityArchivioBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_Indietro;
      Button buttonIndietro = ViewBindings.findChildViewById(rootView, id);
      if (buttonIndietro == null) {
        break missingId;
      }

      id = R.id.downloadButton;
      ImageButton downloadButton = ViewBindings.findChildViewById(rootView, id);
      if (downloadButton == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.textUnilife;
      TextView textUnilife = ViewBindings.findChildViewById(rootView, id);
      if (textUnilife == null) {
        break missingId;
      }

      id = R.id.textView17;
      TextView textView17 = ViewBindings.findChildViewById(rootView, id);
      if (textView17 == null) {
        break missingId;
      }

      id = R.id.uploadButton;
      ImageButton uploadButton = ViewBindings.findChildViewById(rootView, id);
      if (uploadButton == null) {
        break missingId;
      }

      return new ActivityArchivioBinding((ConstraintLayout) rootView, buttonIndietro,
          downloadButton, main, textUnilife, textView17, uploadButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
