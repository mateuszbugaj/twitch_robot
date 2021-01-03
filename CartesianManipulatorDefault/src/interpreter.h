#include <bits/stdc++.h>
#include <Arduino.h>

std::vector<float> interpret(String command, float current_x, float current_y, float current_z)
{
    float x = 0, y = 0, z = 0;
    bool xChange = false, yChange = false, zChange = false;
    command.toLowerCase();

    char str[command.length() + 1];

    // copy string (strcpy) into char array str[] based on command pointer (c_str())
    strcpy(str, command.c_str());

    // split str[] into tokens (strtok) based on space delimiter and return pointer to the begining of the token
    std::vector<String> tokens;
    char *token = strtok(str, " ");
    tokens.push_back(token);

    while (token != NULL)
    {
        token = strtok(NULL, " ");
        tokens.push_back(token);
    }

    // atof - interpreter as double
    for (int i = 0; i < tokens.size(); i++)
    {
        String tok = tokens[i];
        char leadingChar = tok.charAt(0);

        switch (leadingChar)
        {
        case 'x':
            x = atof(tok.substring(1).c_str());
            xChange = true;

            break;

        case 'y':
            y = atof(tok.substring(1).c_str());
            yChange = true;

            break;

        case 'z':
            z = atof(tok.substring(1).c_str());
            zChange = true;

            break;

        }
    }

    if(xChange == false){
        x = current_x;
    }

    if(yChange == false){
        y = current_y;
    }

    if(zChange == false){
        z = current_z;
    }
    
    return std::vector<float> {x, y, z};
}