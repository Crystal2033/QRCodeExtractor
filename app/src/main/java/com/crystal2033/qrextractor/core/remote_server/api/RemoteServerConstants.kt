package com.crystal2033.qrextractor.core.remote_server.api

class RemoteServerConstants {
    companion object URL_API {
        public final const val ORGANIZATION_URL: String = "/api/organizations"
        public final const val BRANCH_URL: String = "$ORGANIZATION_URL/{orgId}/branches"
        public final const val BUILDING_URL: String = "$BRANCH_URL/{branchId}/buildings"
        public final const val CABINET_URL: String = "$BUILDING_URL/{buildingId}/cabinets"
        public final const val CHAIR_URL: String = "$CABINET_URL/{cabinetId}/chairs"
        public final const val DESK_URL: String = "$CABINET_URL/{cabinetId}/desks"
        public final const val PROJECTOR_URL: String = "$CABINET_URL/{cabinetId}/projectors"
        public final const val SYSTEM_UNIT_URL: String = "$CABINET_URL/{cabinetId}/system_units"
        public final const val MONITOR_UNIT_URL: String = "$CABINET_URL/{cabinetId}/monitors"
        public final const val KEYBOARD_UNIT_URL: String = "$CABINET_URL/{cabinetId}/keyboards"
    }

}