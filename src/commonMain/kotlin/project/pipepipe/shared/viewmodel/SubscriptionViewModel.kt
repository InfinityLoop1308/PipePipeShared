package project.pipepipe.shared.viewmodel

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import project.pipepipe.shared.database.DatabaseOperations
import project.pipepipe.shared.uistate.SubscriptionsUiState

class SubscriptionsViewModel : BaseViewModel<SubscriptionsUiState>(SubscriptionsUiState()) {

    suspend fun init() {
        setState {
            it.copy(common = it.common.copy(isLoading = true))
        }

        val feedGroup = DatabaseOperations.getAllFeedGroups()
        val subscriptions = DatabaseOperations.getAllSubscriptions()

        setState {
            it.copy(
                common = it.common.copy(isLoading = false),
                feedGroups = feedGroup,
                subscriptions = subscriptions
            )
        }
    }

    suspend fun createFeedGroup(name: String, iconId: Int){
        DatabaseOperations.insertFeedGroup(name, iconId.toLong())
        val feedGroup = DatabaseOperations.getAllFeedGroups()
        setState {
            it.copy(
                common = it.common.copy(isLoading = false),
                feedGroups = feedGroup,
            )
        }
    }
}