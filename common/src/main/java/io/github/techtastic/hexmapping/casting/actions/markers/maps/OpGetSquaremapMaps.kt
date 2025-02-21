package io.github.techtastic.hexmapping.casting.actions.markers.maps

import io.github.techtastic.hexmapping.integration.squaremap.SquareMapIntegration

class OpGetSquaremapMaps: OpGetMaps(SquareMapIntegration::getMapFromLevel) {
}