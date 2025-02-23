// Generated by view binder compiler. Do not edit!
package com.example.unilife.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.unilife.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentVisualizzaSpesaBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView RVPartecipantiPagamento;

  @NonNull
  public final Button buttonIndietro;

  @NonNull
  public final Button buttonPaga;

  @NonNull
  public final ConstraintLayout linearLayout;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final ImageButton modificaButton;

  @NonNull
  public final TextView textView22;

  @NonNull
  public final TextView textView24;

  @NonNull
  public final TextView textView25;

  @NonNull
  public final TextView textViewPrezzo;

  @NonNull
  public final TextView textViewQuota;

  @NonNull
  public final TextView textViewTitolo;

  private FragmentVisualizzaSpesaBinding(@NonNull ConstraintLayout rootView,
      @NonNull RecyclerView RVPartecipantiPagamento, @NonNull Button buttonIndietro,
      @NonNull Button buttonPaga, @NonNull ConstraintLayout linearLayout,
      @NonNull LinearLayout linearLayout2, @NonNull ImageButton modificaButton,
      @NonNull TextView textView22, @NonNull TextView textView24, @NonNull TextView textView25,
      @NonNull TextView textViewPrezzo, @NonNull TextView textViewQuota,
      @NonNull TextView textViewTitolo) {
    this.rootView = rootView;
    this.RVPartecipantiPagamento = RVPartecipantiPagamento;
    this.buttonIndietro = buttonIndietro;
    this.buttonPaga = buttonPaga;
    this.linearLayout = linearLayout;
    this.linearLayout2 = linearLayout2;
    this.modificaButton = modificaButton;
    this.textView22 = textView22;
    this.textView24 = textView24;
    this.textView25 = textView25;
    this.textViewPrezzo = textViewPrezzo;
    this.textViewQuota = textViewQuota;
    this.textViewTitolo = textViewTitolo;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentVisualizzaSpesaBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentVisualizzaSpesaBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_visualizza_spesa, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentVisualizzaSpesaBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.RVPartecipantiPagamento;
      RecyclerView RVPartecipantiPagamento = ViewBindings.findChildViewById(rootView, id);
      if (RVPartecipantiPagamento == null) {
        break missingId;
      }

      id = R.id.buttonIndietro;
      Button buttonIndietro = ViewBindings.findChildViewById(rootView, id);
      if (buttonIndietro == null) {
        break missingId;
      }

      id = R.id.buttonPaga;
      Button buttonPaga = ViewBindings.findChildViewById(rootView, id);
      if (buttonPaga == null) {
        break missingId;
      }

      ConstraintLayout linearLayout = (ConstraintLayout) rootView;

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.modificaButton;
      ImageButton modificaButton = ViewBindings.findChildViewById(rootView, id);
      if (modificaButton == null) {
        break missingId;
      }

      id = R.id.textView22;
      TextView textView22 = ViewBindings.findChildViewById(rootView, id);
      if (textView22 == null) {
        break missingId;
      }

      id = R.id.textView24;
      TextView textView24 = ViewBindings.findChildViewById(rootView, id);
      if (textView24 == null) {
        break missingId;
      }

      id = R.id.textView25;
      TextView textView25 = ViewBindings.findChildViewById(rootView, id);
      if (textView25 == null) {
        break missingId;
      }

      id = R.id.textViewPrezzo;
      TextView textViewPrezzo = ViewBindings.findChildViewById(rootView, id);
      if (textViewPrezzo == null) {
        break missingId;
      }

      id = R.id.textViewQuota;
      TextView textViewQuota = ViewBindings.findChildViewById(rootView, id);
      if (textViewQuota == null) {
        break missingId;
      }

      id = R.id.textViewTitolo;
      TextView textViewTitolo = ViewBindings.findChildViewById(rootView, id);
      if (textViewTitolo == null) {
        break missingId;
      }

      return new FragmentVisualizzaSpesaBinding((ConstraintLayout) rootView,
          RVPartecipantiPagamento, buttonIndietro, buttonPaga, linearLayout, linearLayout2,
          modificaButton, textView22, textView24, textView25, textViewPrezzo, textViewQuota,
          textViewTitolo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
