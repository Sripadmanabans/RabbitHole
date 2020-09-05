package com.adjectivemonk2.rabbithole

import com.adjectivemonk2.rabbithole.qualifier.PrivateKey
import com.adjectivemonk2.rabbithole.qualifier.PublicKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
internal object BuildConfigModule {

  @Provides @PublicKey fun publicKey(): String = BuildConfig.PUBLIC_KEY

  @Provides @PrivateKey fun privateKey(): String = BuildConfig.PRIVATE_KEY
}
