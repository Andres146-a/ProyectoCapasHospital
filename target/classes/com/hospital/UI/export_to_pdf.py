from fpdf import FPDF
import sys
import json

class PDF(FPDF):
    def header(self):
        self.set_font('Arial', 'B', 12)
        self.cell(0, 10, 'Consulta Médica - Reporte', border=0, ln=1, align='C')
        self.ln(10)

    def chapter_title(self, title):
        self.set_font('Arial', 'B', 12)
        self.cell(0, 10, title, border=0, ln=1, align='L')
        self.ln(5)

    def chapter_body(self, body):
        self.set_font('Arial', '', 10)
        self.multi_cell(0, 10, body)
        self.ln()

def generate_pdf(data):
    pdf = PDF()
    pdf.add_page()

    pdf.chapter_title("Detalles de Consultas")
    for item in data:
        pdf.chapter_body(f"ID: {item['id']}")
        pdf.chapter_body(f"Paciente: {item['paciente']}")
        pdf.chapter_body(f"Fecha de Ingreso: {item['fecha_ingreso']}")
        pdf.chapter_body(f"Enfermedad: {item['enfermedad']}")
        pdf.chapter_body(f"Doctor: {item['doctor']}")
        pdf.chapter_body(f"Descripción: {item['descripcion']}")
        pdf.ln(10)

    pdf.output("consulta_medica.pdf")
    print("PDF generado exitosamente como 'consulta_medica.pdf'")

if __name__ == "__main__":
    # El programa espera un JSON como argumento
    if len(sys.argv) < 2:
        print("Uso: python export_to_pdf.py '<json_data>'")
    else:
        json_data = json.loads(sys.argv[1])
        generate_pdf(json_data)
