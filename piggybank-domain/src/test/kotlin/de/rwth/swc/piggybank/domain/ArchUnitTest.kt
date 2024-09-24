package de.rwth.swc.piggybank.domain

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import org.junit.jupiter.api.Test


object ArchitectureTest {

    private val classes = ClassFileImporter().importPackages("de.rwth.swc.piggybank.domain")
    @Test
    fun `architecture test`() {
        slices()
            .matching("de.rwth.swc.piggybank.domain.(*)..")
            .should()
            .beFreeOfCycles()
            .check(classes)

        slices()
            .matching("de.rwth.swc.piggybank.domain.(*)..")
            .should()
            .notDependOnEachOther()
            .ignoreDependency(DescribedPredicate.alwaysTrue(), resideInAPackage("de.rwth.swc.piggybank.domain.shared.."))
            .check(classes)
    }
}