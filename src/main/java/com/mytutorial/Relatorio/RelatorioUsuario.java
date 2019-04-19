package com.mytutorial.Relatorio;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class RelatorioUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	public byte[] gerarRelatorio(HashMap<String, Object> usuarios) throws JRException {

		// Abrindo o arquivo
		try {
			InputStream arq = RelatorioUsuario.class.getResourceAsStream("/jasper/RelatorioUsuario.jasper");

			return JasperRunManager.runReportToPdf(arq, usuarios, new JREmptyDataSource());
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO AO CRIAR RELATÓRIO: " + erro);
		}
		return null;
	}

}
