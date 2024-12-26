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
        pdf.chapter_body(f"ID: {item.get('id', 'N/A')}")
        pdf.chapter_body(f"Paciente: {item.get('paciente', 'N/A')}")
        pdf.chapter_body(f"Fecha de Ingreso: {item.get('fecha_ingreso', 'N/A')}")
        pdf.chapter_body(f"Enfermedad: {item.get('enfermedad', 'N/A')}")
        pdf.chapter_body(f"Doctor: {item.get('doctor', 'N/A')}")
        pdf.chapter_body(f"Descripción: {item.get('descripcion', 'N/A')}")
        pdf.ln(10)

    pdf.output("consulta_medica.pdf")
    print("PDF generado exitosamente como 'consulta_medica.pdf'")

if __name__ == "__main__":
    try:
        if len(sys.argv) > 1:
            # Leer JSON desde argumento
            print("Intentando cargar JSON desde argumento...")
            json_data = json.loads(sys.argv[1])
        else:
            # Leer JSON desde archivo si no hay argumento
            print("No se proporcionó JSON como argumento, leyendo desde 'data.json'...")
            with open("data.json", "r") as file:
                json_data = json.load(file)

        # Generar PDF con los datos
        generate_pdf(json_data)

    except FileNotFoundError:
        print("Error: El archivo 'data.json' no existe.")
    except json.JSONDecodeError as e:
        print(f"Error: El JSON proporcionado es inválido. Detalles: {e}")
    except Exception as e:
        print(f"Error inesperado: {e}")
