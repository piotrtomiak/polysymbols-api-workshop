package com.github.piotrtomiak.polysymbolsapiworkshop

import com.intellij.aqua.runners.cypress.CypressRunConfigurationProducer
import com.intellij.javascript.testing.JSTestRunnerManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager

internal fun isCypressAvailable(psiElement: PsiElement) =
  JSTestRunnerManager.getInstance().findProducers(if (psiElement is PsiDirectory) psiElement else psiElement.containingFile.originalFile)
    .any {
      it is CypressRunConfigurationProducer
    }

internal fun isCypressAvailable(project: Project): Boolean {
  val virtualDir = project.guessProjectDir()
  return when {
    virtualDir == null -> false
    !virtualDir.isValid -> false
    else -> PsiManager.getInstance(project).findDirectory(virtualDir)?.let { isCypressAvailable(it) } ?: false
  }
}
