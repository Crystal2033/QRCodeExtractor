package com.crystal2033.qrextractor.add_object_feature.place_choice.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.viewmodel.PlaceChoiceViewModel
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.vm_view_communication.PlaceChoiceEvent
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.vm_view_communication.UIPlaceChoiceEvent
import com.crystal2033.qrextractor.ui.text_elements.DropListView
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PlaceChoiceView(
    viewModel: PlaceChoiceViewModel,
    actionBeforeNavigate: () -> Unit,
    onNavigate: (UIPlaceChoiceEvent.Navigate) -> Unit,
    onPopBack: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIPlaceChoiceEvent.Navigate -> {
                    actionBeforeNavigate()
                    onNavigate(event)
                }

                UIPlaceChoiceEvent.PopBack -> {
                    actionBeforeNavigate()
                    onPopBack()
                }
            }

        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DropListView(
                fieldName = stringResource(id = R.string.branch_translate),
                listOfObjects = viewModel.listOfBranches.map { Pair(it.id, it.name) },
                onValueChanged = { chosenId ->
                    viewModel.onEvent(PlaceChoiceEvent.OnBranchChanged(chosenId))
                },
                currentChosenValue = viewModel.selectedBranch.value?.name ?: ""
            )
            Spacer(modifier = Modifier.height(15.dp))
            DropListView(
                fieldName = stringResource(id = R.string.building_translate),
                listOfObjects = viewModel.listOfBuildings.map {
                    Pair(
                        it.id,
                        it.address
                    )
                },
                onValueChanged = { chosenId ->
                    viewModel.onEvent(PlaceChoiceEvent.OnBuildingChanged(chosenId))
                },
                isEnabled = viewModel.isAbleToChooseBuilding(),
                currentChosenValue = viewModel.selectedBuilding.value?.address ?: ""
            )
            Spacer(modifier = Modifier.height(15.dp))
            DropListView(
                fieldName = stringResource(id = R.string.cabinet_translate),
                listOfObjects = viewModel.listOfCabinets.map {
                    Pair(
                        it.id,
                        it.name
                    )
                },
                onValueChanged = { chosenId ->
                    viewModel.onEvent(PlaceChoiceEvent.OnCabinetChanged(chosenId))
                },
                isEnabled = viewModel.isAbleToChooseCabinet(),
                currentChosenValue = viewModel.selectedCabinet.value?.name ?: ""
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    viewModel.onEvent(PlaceChoiceEvent.OnContinueClicked)
                },
                enabled = viewModel.isPlaceChosen(),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.next_translate))
            }
        }
    }
}