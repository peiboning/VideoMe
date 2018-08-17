package org.com.gradleplugin

import com.check_res.model.ResourceInfo
import org.com.gradleplugin.task.CheckResource
import org.gradle.api.Plugin
import org.gradle.api.Project;

class Hello implements Plugin<Project>{
    private static String SPath = "src/main/res/values/strings.xml";
    private static String CPath = "src/main/res/values/colors.xml";
    private static String dPath = "src/main/res/values/strings.xml";
    private Map<String, ResourceInfo> StringMap = new HashMap<>();
    private Map<String, ResourceInfo> ClolorMap = new HashMap<>();
    private Map<String, ResourceInfo> DrawableMap = new HashMap<>();
    List<ResourceInfo> strings = new ArrayList<>();

    @Override
    void apply(Project project) {
        CheckResource checkResource = project.getTasks().create("checkResource", CheckResource.class);
        checkResource.doLast {
            System.out.print("---------start checkResource ---------")
            new Thread(){
                @Override
                void run() {
                    if(checkResource.getColor()){
                        System.out.print("---------start check color---------")
                    }

                    if(checkResource.getString()){
                        System.out.print("---------start check string---------")
                        checkString(project)
                        writeLog(project, "string", strings)
                        System.out.print("---------end check string---------")
                    }

                    if(checkResource.getDrawable()){
                        System.out.print("---------start check drawable---------")
                    }
                }
            }.start()
        }

    }

    void checkString(Project project){
        if(project.childProjects.size()>1){
            project.childProjects.each {
                checkString(it.value)
            }
        }else{
            String path = "${project.projectDir}" + File.separator + SPath;

            File file = new File(path);
            if(!file.exists()){
                return
            }

            def list = new XmlParser().parse(file);
            list.string.each{
                String name = it.@name;
                String value = it.text();
                ResourceInfo info = new ResourceInfo("string");
                info.setProject(project.name)
                info.setName(name)
                info.setValue(value)
                if(StringMap.containsKey(name)){
                    strings.add(info)
                    if(!strings.contains(StringMap.get(name))){
                        strings.add(StringMap.get(name))
                    }
                }else{
                    StringMap.put(name, info)
                }
            }
        }
    }

    void writeLog(Project project, String name, List<ResourceInfo> infos){
        if(null != infos && infos.size()>0){
            File file = new File(project.getRootDir(), name);
            if(!file.exists()){
                file.createNewFile();
            }

            OutputStream out = new FileOutputStream(file);
            for(ResourceInfo info : infos){
                out.write(info.toString().getBytes())
                out.write("\n".toString().getBytes())
            }

        }

    }

}