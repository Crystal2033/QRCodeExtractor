package com.crystal2033.qrextractor.core.remote_server.api

class RemoteServerConstants {
    companion object URL_API {
        public final const val ORGANIZATION_URL: String = "/api/organizations"
        public final const val BRANCH_URL: String = "$ORGANIZATION_URL/{orgId}/branches"
        public final const val BUILDING_URL: String = "$BRANCH_URL/{branchId}/buildings"
        public final const val CABINET_URL: String = "$BUILDING_URL/{buildingId}/cabinets"
        public final const val CHAIR_URL: String = "$CABINET_URL/chairs"
    }

}