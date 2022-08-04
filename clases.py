class clase(object):
    def __init__(self,tipo, metodos, super):
        self.tipo = tipo
        self.metodos = metodos
        self.super = super

class VMT(object):

    clases = {}

    def existeClase(self, tipo) :
        return any(c == tipo for c in self.clases)

    def nuevaClase(self, tipo, metodos, super = None):
        self.clases[tipo] =clase(tipo, metodos, super)

    def describir(self, nombre):
        metodos = {}
        self.obtenerMetodos(nombre, metodos)

        for m in metodos.items():
            print('{} -> {} :: {}'.format(m[0], m[1], m[0]))

    def obtenerMetodos(self, nombre, metodos):
        c = self.clases[nombre]
        if c.super != None:
            self.obtenerMetodos(c.super, metodos)

        for m in c.metodos:
            metodos[m] = c.tipo
    


vmt = VMT()
while True:

    command = input(">").strip().split(" ")
    l = len(command)

    if command[0] == "": continue

    if command[0] == "CLASS":

        if l == 1:
            print("Error: no fue provisto <tipo>")
            continue
        if l == 2:
            print("Error: no fue provisto [<nombre>]")
            continue


        haySuper = False
        if command[2] == ":":
            if l == 3:
                print("Error: sintaxis incorrecta para <tipo>")
                continue
            if l == 4:
                print("Error: no fue provisto [<nombre>]")
                continue
            haySuper = True

        tipo = command[1]
        if (vmt.existeClase(tipo)):
            print("tipo " + tipo + " ya existe")
            continue 
        superClase = None
        i = 2
        if haySuper: 
            superClase = command[3]
            if (not vmt.existeClase(superClase)):
                print("super tipo " + superClase + " no existe")
                continue 
            i = 4
        nombre = command[i:]
        vmt.nuevaClase(tipo, nombre, superClase)
        
    elif command[0] == "DESCRIBIR":

        if l == 1:
            print("Error: no fue provisto <nombre>")
            continue
        if l > 2:
            print("Error: parametro no reconocido: " + command[2])
            continue

        tipo = command[1]
        if (not vmt.existeClase(tipo)):
            print("tipo " + tipo + " no existe")
            continue 
    
        vmt.describir(tipo)

    elif command[0] == "SALIR": exit()
    else: print("comando invalido")