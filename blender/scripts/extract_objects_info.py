import bpy
import sys

# Creates an operator.
class ExtractObjectsInfoOperator(bpy.types.Operator):
    bl_idname = "object.extract_objects_info_operator"
    bl_label = "Extract Objects Info Operator"

    def execute(self, context):
        print('Creating object info text file...')
        try:
            file = open('/home/jorge/object_info.txt','w')
            print('Object info text file was created on /home/jorge')
            numberOfObjects = len(bpy.data.objects)
            file.write('Number of scene objects: ' + str(numberOfObjects) + '\n\n')
            movables_string_start = 'MOVABLES_LIST_START_OBJECTS\n'
            movables_string_end = 'MOVABLES_LIST_END_OBJECTS\n'
            static_string = 'STATICS_LIST_OBJECTS\n'
            # Loop over the scene objects
            for x in range(0, numberOfObjects):
                objectName = bpy.data.objects[x].name
                if 'Movable' in objectName:
                    if 'start' in objectName:
                        movables_string_start += objectName + '\n'
                        movables_string_start += str(bpy.data.objects[x].location) + '\n'
                        movables_string_start += str(bpy.data.objects[x].rotation_euler) + '\n'
                        movables_string_start += str(bpy.data.objects[x].rotation_quaternion) + '\n\n'
                    else:
                        movables_string_end += objectName + '\n'
                        movables_string_end += str(bpy.data.objects[x].location) + '\n'
                        movables_string_end += str(bpy.data.objects[x].rotation_euler) + '\n'
                        movables_string_end += str(bpy.data.objects[x].rotation_quaternion) + '\n\n'
                elif 'Static' in objectName:
                    static_string += objectName + '\n'
                    static_string += str(bpy.data.objects[x].location) + '\n'
                    static_string += str(bpy.data.objects[x].rotation_euler) + '\n'
                    static_string += str(bpy.data.objects[x].rotation_quaternion) + '\n\n'
            # Write over the text file
            file.write(movables_string_start)
            file.write(movables_string_end)
            file.write(static_string) 
            file.close()
        except:
            print('Unable to create text file!')
        return {'FINISHED'}

# Registers the operator
def register():
    bpy.utils.register_class(ExtractObjectsInfoOperator)

if __name__ == "__main__":
    register()
 
# Run the script to register it in Blender, then press space in the 3D view
# and search the operator in the text box. Click on it to execute.
