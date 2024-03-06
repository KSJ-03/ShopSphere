package com.example.shopsphere.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.example.shopsphere.util.Constants.INTRODUCTION_SP
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseFirestoreDatabase() = Firebase.firestore

    @Provides
    fun provideIntroductionSP(application: Application) =
        application.getSharedPreferences(INTRODUCTION_SP, MODE_PRIVATE)
}