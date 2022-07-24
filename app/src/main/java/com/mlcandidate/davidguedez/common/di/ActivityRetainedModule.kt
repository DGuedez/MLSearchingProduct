package com.mlcandidate.davidguedez.common.di

import com.mlcandidate.davidguedez.common.domain.repositories.ProductsRepository
import com.mlcandidate.davidguedez.common.utils.CoroutineDispatcherProvider
import com.mlcandidate.davidguedez.common.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindProductRepository(repository: ProductsRepository): ProductsRepository

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatcherProvider):
            DispatchersProvider
}

