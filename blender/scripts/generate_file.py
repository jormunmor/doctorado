import bpy
import sys

# Creates an operator. This operator generates a dummy text file.
class GenerateFileOperator(bpy.types.Operator):
    bl_idname = "object.generate_file_operator"
    bl_label = "Generate File Operator"

    def execute(self, context):
        print('Creating new dummy text file...')
        try:
            file = open('/home/jorge/dummy.txt','w')
            file.close()
        except:
            print('Unable to create text file!')
            return {'FINISHED'}
        print("A dummy text file was created on /home/jorge")
        return {'FINISHED'}

# Registers the operator
def register():
    bpy.utils.register_class(GenerateFileOperator)

if __name__ == "__main__":
    register()
 
# Run the script to register it in Blender, then press space in the 3D view
# and search the operator in the text box. Click on it to execute. A dummy
# file must appear on /home/jorge

